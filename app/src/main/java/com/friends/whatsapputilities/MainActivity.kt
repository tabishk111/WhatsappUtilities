package com.friends.whatsapputilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.friends.whatsapputilities.adapter.ViewPagerAdapter
import com.friends.whatsapputilities.fragments.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val navIcons = intArrayOf(
        R.drawable.status_saver,
        R.drawable.status_saver,
        R.drawable.status_saver,
        R.drawable.status_saver,
        R.drawable.status_saver
    )


    private val navLabels = intArrayOf(
        R.string.status_saver,
        R.string.direct_chat,
        R.string.walk_and_chat,
        R.string.deleted_message,
        R.string.qr_creator
    )
    private val navIconsActive = intArrayOf(
        R.drawable.status_saver_active,
        R.drawable.status_saver_active,
        R.drawable.status_saver_active,
        R.drawable.status_saver_active,
        R.drawable.status_saver_active
    )
    var tabsPositon: String? = ""
    var tabsIndex = arrayOf(
        0, 1, 2, 3, 4
    )
    var drawerLayout: DrawerLayout? = null
    lateinit var drawerIv: ImageView
    var navigationView: NavigationView? = null
    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewpager)
        drawerIv = findViewById(R.id.drawerIv)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        navigationView!!.setNavigationItemSelectedListener(this)
        drawerIv.setOnClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }

        setupViewPager()
    }


    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(
            supportFragmentManager
        )
        var status_saver_indx: Int
        var direct_chat_indx: Int
        var walk_and_chat_indx: Int
        var deleted_message_indx: Int
        var qr_creator_indx: Int
        if (tabsPositon != null && tabsPositon!!.isNotEmpty()) {

            status_saver_indx = 0
            direct_chat_indx = 1
            walk_and_chat_indx = 2
            deleted_message_indx = 3
            qr_creator_indx = 4

            navIcons[status_saver_indx] = R.drawable.status_saver
            navIcons[direct_chat_indx] = R.drawable.status_saver
            navIcons[walk_and_chat_indx] = R.drawable.status_saver
            navIcons[deleted_message_indx] = R.drawable.status_saver
            navIcons[qr_creator_indx] = R.drawable.status_saver



            navIconsActive[status_saver_indx] = R.drawable.status_saver_active
            navIconsActive[direct_chat_indx] = R.drawable.status_saver_active
            navIconsActive[walk_and_chat_indx] = R.drawable.status_saver_active
            navIconsActive[deleted_message_indx] = R.drawable.status_saver_active
            navIconsActive[qr_creator_indx] = R.drawable.status_saver_active


            navLabels[status_saver_indx] = R.string.status_saver
            navLabels[direct_chat_indx] = R.string.direct_chat
            navLabels[walk_and_chat_indx] = R.string.walk_and_chat
            navLabels[deleted_message_indx] = R.string.deleted_message
            navLabels[qr_creator_indx] = R.string.qr_creator

            val tabs_fragment = arrayOfNulls<Fragment>(5)
            tabs_fragment[status_saver_indx] = StatusSaverFragment()
            tabs_fragment[direct_chat_indx] = DirectChatFragment()
            tabs_fragment[walk_and_chat_indx] = WalkAndChatFragment()
            tabs_fragment[deleted_message_indx] = DeletedMessageFragment()
            tabs_fragment[qr_creator_indx] = QRCreatorFragment()

            for (i in 0..5) {
                adapter.addFragment(tabs_fragment[i], resources.getString(navLabels[i]))
            }
        } else {

            adapter.addFragment(StatusSaverFragment(), resources.getString(R.string.status_saver))
            adapter.addFragment(DirectChatFragment(),resources.getString(R.string.direct_chat))
            adapter.addFragment(WalkAndChatFragment(), resources.getString(R.string.walk_and_chat))
            adapter.addFragment(DeletedMessageFragment(),resources.getString(R.string.deleted_message))
            adapter.addFragment(QRCreatorFragment(), resources.getString(R.string.qr_creator))
        }
        viewPager!!.offscreenPageLimit = 5
        viewPager!!.adapter = adapter
        viewPager!!.currentItem = 0
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabAt = tabLayout!!.getTabAt(viewPager!!.currentItem)

                val tabView = tab.customView

                val tab_label = tabView!!.findViewById<TextView>(R.id.nav_label)
                val tab_icon = tabView.findViewById<ImageView>(R.id.nav_icon)
                tab_label.text =
                    resources.getString(navLabels[tab.position])
                tab_label.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                tab_icon.setImageResource(navIconsActive[tabsIndex[tab.position].toInt()])

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                if (tabView != null) {
                    val tab_label = tabView.findViewById<TextView>(R.id.nav_label)
                    val tab_icon = tabView.findViewById<ImageView>(R.id.nav_icon)
                    tab_label.text =
                        resources.getString(navLabels.get(tabsIndex.get(tab.position).toInt()))
                    tab_label.setTextColor(resources.getColor(R.color.black))
                    tab_icon.setImageResource(navIcons[tabsIndex[tab.position].toInt()])
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {


            }
        })
        for (i in 0 until tabLayout!!.tabCount) {
            val tab = LayoutInflater.from(this).inflate(R.layout.nav_tab, null) as ConstraintLayout
            val tab_label = tab.findViewById<TextView>(R.id.nav_label)
            val tab_icon = tab.findViewById<ImageView>(R.id.nav_icon)
            tab_label.text = resources.getString(navLabels[tabsIndex[i].toInt()])
            if (i == 0) {
                tab_label.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                tab_icon.setImageResource(navIconsActive[tabsIndex[i].toInt()])
            } else {
                tab_icon.setImageResource(navIcons[tabsIndex[i].toInt()])
            }
            tabLayout!!.getTabAt(i)!!.customView = tab
        }
        viewPager!!.currentItem = 0
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {


            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPager!!.currentItem = 0
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return true
    }

}