package com.example.whr.myapplication.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityOptions
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.example.whr.myapplication.R

open class BaseSwipeBackActivity : AppCompatActivity() {

    private val lastPoint = PointF()
    private var tranX = 0f
    private val windowSize = Point()
    private var touchSlop = 0
    private var swipeType = false
    private var isFirst = true
    private lateinit var transView: View
    private lateinit var decorView: ViewGroup
    private val shadowMax = 0xa0
    private var shadowPer = 0f
    private var shadowDp = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SwipeBackTheme)
        convertActivityToTranslucentAfterL(this)
        //设置背景透明
        window.setBackgroundDrawableResource(R.color.colorTransparent)
        //背景view
        decorView = window.decorView as FrameLayout
        //初始化屏幕尺寸
        windowManager.defaultDisplay.getSize(windowSize)
        //获得系统滑动阀值
        touchSlop = ViewConfiguration.get(this).scaledTouchSlop
        //计算阴影单位改变值
        shadowPer = shadowMax / windowSize.x.toFloat()
        shadowDp = resources.getDimension(R.dimen.swipe_shadow_size)
        tranX = -shadowDp
    }

    /**
     * 反射设置windowIsTranslucent
     */
    private fun convertActivityToTranslucentAfterL(activity: Activity) {
        try {
            val getActivityOptions = Activity::class.java.getDeclaredMethod("getActivityOptions")
            getActivityOptions.isAccessible = true
            val options = getActivityOptions.invoke(activity)

            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val convertToTranslucent = Activity::class.java.getDeclaredMethod("convertToTranslucent",
                translucentConversionListenerClazz, ActivityOptions::class.java)
            convertToTranslucent.isAccessible = true
            convertToTranslucent.invoke(activity, null, options)
        } catch (t: Throwable) {
        }
    }

    /**
     * 设置背景
     */
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        findViewById<ViewGroup>(android.R.id.content).let {
            //viewGroup,将背景和content绑定在一起
            val viewGroup = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(windowSize.x + shadowDp.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
                translationX = tranX
            }
            //背景View
            View(this).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                setBackgroundResource(R.drawable.edge_shadow)
                viewGroup.addView(this)
            }
            //contentView
            it.getChildAt(0).apply {
                val params = layoutParams
                params.width = windowSize.x
                layoutParams = params
                fitsSystemWindows = true
                translationX = shadowDp
                it.removeView(this)
                viewGroup.addView(this)
            }
            it.addView(viewGroup)
            transView = viewGroup
        }
    }

    /**
     * 判断当满足特定情况时触发侧滑事件
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                isFirst = true
                swipeType = false
                lastPoint.set(ev.x, ev.y)
            }
            MotionEvent.ACTION_MOVE -> {
                val changeX = ev.x - lastPoint.x
                val changeY = ev.y - lastPoint.y
                if (isFirst && Math.abs(changeX) > touchSlop && Math.abs(changeY) > Math.abs(changeX) * 1.5) {
                    isFirst = false
                }
                if (isFirst && Math.abs(changeX) > touchSlop && Math.abs(changeX) > Math.abs(changeY) * 1.5) {
                    swipeType = true
                }
                if (swipeType) {
                    if (tranX + changeX < -shadowDp) {
                        return true
                    }
                    tranX += changeX
                    val a = shadowMax - tranX * shadowPer
                    val shadow = a.toInt().toString(16).let {
                        if (it.length == 1) {
                            "0$it"
                        } else it
                    }
                    decorView.setBackgroundColor(Color.parseColor("#${shadow}000000"))
                    transView.translationX = tranX
                    lastPoint.set(ev.x, ev.y)
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (swipeType) {
                    if (tranX >= windowSize.x / 3) {
                        startAnim(true)
                    } else {
                        startAnim(false)
                    }
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun startAnim(isExit: Boolean) {
        ObjectAnimator().apply {
            duration = 300
            if (isExit) {
                setFloatValues(tranX, windowSize.x.toFloat() - shadowDp)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        finish()
                        // 关闭 window 退出动画
                        overridePendingTransition(0,0)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
            } else {
                setFloatValues(tranX, -shadowDp)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        tranX = -shadowDp
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
            }
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                tranX = animation.animatedValue as Float
                val a = shadowMax - tranX * shadowPer
                val shadow = a.toInt().toString(16).let {
                    if (it.length == 1) {
                        "0$it"
                    } else it
                }
                decorView.setBackgroundColor(Color.parseColor("#${shadow}000000"))
                transView.translationX = tranX
            }
            start()
        }
    }
}
