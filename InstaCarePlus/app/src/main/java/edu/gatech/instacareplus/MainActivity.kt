package edu.gatech.instacareplus
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils
import edu.gatech.instacareplus.ServiceManager.VitalsManager
import edu.gatech.instacareplus.databinding.ActivityMainBinding
import model.VitalPoint
import model.VitalsRegistrationRequest
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainBinding
    lateinit var v1: LineDataSet
    lateinit var v2: LineDataSet
    lateinit var v3: LineDataSet
    var patientId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        patientId = intent.getIntExtra("userId", -1)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_healthmonitor, R.id.nav_consultation, R.id.nav_supply, R.id.nav_medsched), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        val vitalsManager = VitalsManager()
        Thread(Runnable {
            try {
                Utils.init(applicationContext)
                val entries1: ArrayList<Entry> = ArrayList()
                val entries2: ArrayList<Entry> = ArrayList()
                val entries3: ArrayList<Entry> = ArrayList()

                val inputStream1: InputStream? = assets?.open("spo2_sample1.csv")
                val lineList1 = mutableListOf<String>()
                inputStream1?.bufferedReader()?.forEachLine { lineList1.add(it) }

                val inputStream2: InputStream? = assets?.open("bp_sample1.csv")
                val lineList2 = mutableListOf<String>()
                inputStream2?.bufferedReader()?.forEachLine { lineList2.add(it) }

                val inputStream3: InputStream? = assets?.open("ecg_sample1.csv")
                val lineList3 = mutableListOf<String>()
                inputStream3?.bufferedReader()?.forEachLine { lineList3.add(it) }

                val size = minOf(lineList1.size, lineList2.size, lineList3.size)
                var index = 0.0
                var l_index = 0

                var vitalsBatch = ArrayList<VitalPoint>()

                while(true) {
                    val line1 = lineList1[l_index]
                    val line2 = lineList2[l_index]
                    val line3 = lineList3[l_index]

                    val p1 = line1.substring(line1.indexOf(',') + 1)
                    val p2 = line2.substring(line2.indexOf(',') + 1)
                    val p3 = line3.substring(line3.indexOf(',') + 1)

                    entries1.add(Entry(index.toFloat(), p1.toFloat()))
                    entries2.add(Entry(index.toFloat(), p2.toFloat()))
                    entries3.add(Entry(index.toFloat(), p3.toFloat()))

                    val time = System.currentTimeMillis()
                    val vitalPoint = VitalPoint()
                    vitalPoint.spo2 = p1.toDouble()
                    vitalPoint.bp = p2.toDouble()
                    vitalPoint.ecg = p2.toDouble()
                    vitalPoint.timeMillis = time
                    vitalsBatch.add(vitalPoint)

                    if (vitalsBatch.size == 3) {
                        val vitalsRegistrationRequest = VitalsRegistrationRequest()
                        vitalsRegistrationRequest.patientId = patientId
                        vitalsRegistrationRequest.batchedVitals = vitalsBatch
                        vitalsManager.registerVitals(vitalsRegistrationRequest) {}
                        vitalsBatch = ArrayList()
                    }

                    v1 = LineDataSet(entries1, "")
                    v2 = LineDataSet(entries2, "")
                    v3 = LineDataSet(entries3, "")

                    v1.setDrawValues(false)
                    v2.setDrawValues(false)
                    v3.setDrawValues(false)

                    Thread.sleep(1000)
                    index += 1
                    l_index = (l_index + 1)%size

                }
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }).start()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}