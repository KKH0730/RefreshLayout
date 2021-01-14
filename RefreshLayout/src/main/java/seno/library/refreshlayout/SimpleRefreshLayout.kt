package seno.library.refreshlayout

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import kotlin.properties.Delegates

class SimpleRefreshLayout (context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private var progressbar: ProgressBar
    private var translateUp: Animation
    private var playAnim = false
    private var mListener: OnRefreshListener? = null
    private var parentHeight by Delegates.notNull<Int>()


    init {
        var inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view =  inflater.inflate(R.layout.refresh_layout, this)
        progressbar = findViewById(R.id.progressBar)

        var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        parentHeight = windowManager.defaultDisplay.height

        translateUp = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        translateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                progressbar.visibility = View.GONE
                progressbar.y = 0.0f
                playAnim = false
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        setScrollEvent(view)
    }

    private fun setScrollEvent(view: View) {
        view.findViewById<View>(R.id.refreshLayout).setOnTouchListener { v, event: MotionEvent? ->
            detector.onTouchEvent(event!!)
            if (event!!.action == MotionEvent.ACTION_UP) {
                if (playAnim) {
                    if (mListener != null) mListener!!.onRefreshed()
                } else {
                    progressbar.startAnimation(translateUp)
                }
            }
            true
        }
    }


    private val detector = GestureDetector(context, object: GestureDetector.OnGestureListener{
        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            var distance: Float = progressbar.y - distanceY
            progressbar.visibility = VISIBLE
            if (distance > parentHeight / 8) {
                distance = (parentHeight / 8).toFloat()
                playAnim = true
            }
            progressbar.y = distance
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return false;
        }
    })

    fun setRefreshing(flag: Boolean) {
        if (!flag) {
            progressbar.startAnimation(translateUp)
        }
    }

    fun setRefreshListener(listener: OnRefreshListener) {
        mListener = listener
    }
}