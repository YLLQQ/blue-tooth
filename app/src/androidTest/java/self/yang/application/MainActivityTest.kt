package self.yang.application

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents.getIntents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.truth.content.IntentSubject.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testEvent() {
        // GIVEN
        /**
         * 我们使用新的 ActivityScenario API 来启动 LoginActivity。
         * 它将会创建一个 activity，并进入用户可见并能够输入的 resumed 状态。
         * ActivityScenario 处理与系统的所有同步，并为你应测试的常见场景提供支持，例如你的应用如何处理被系统销毁和重建。
         */
        val scenario = launch(MainActivity::class.java)

        // WHEN
        /**
         * 使用 Espresso 视图交互库将文本输入到字段中，然后点击 UI 中的按钮。
         * 与 ActivityScenario 类似，Espresso 为你处理多线程和同步，并提供可读且流畅的 API 以创建测试。
         */
        onView(withId(R.id.editText)).perform(typeText("test_user"))
        onView(withId(R.id.button)).perform(click())

        // THEN
        /**
         * 使用新的 Intents.getIntents() Espresso API 来返回捕获的意图列表。
         * 然后，我们使用 IntentSubject.assertThat() 验证捕获的意图
         */
        assertThat(getIntents().first()).hasComponentClass(DisplayMessageActivity::class.java)
    }

}
