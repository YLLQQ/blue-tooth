package self.yang.application

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "self.yang.application.MESSAGE"
const val USER_INPUT = "USER_INPUT"

class MainActivity : AppCompatActivity() {

	/**
	 * 启动发送邮件的Activity
	 *
	 * 您的应用可能还希望使用 Activity 中的数据执行某些操作，例如发送电子邮件、短信或状态更新。
	 * 在这种情况下，您的应用自身可能不具有执行此类操作所需的 Activity，因此您可以改为利用设备上其他应用提供的 Activity 为您执行这些操作。
	 * 这便是 Intent 的真正价值所在：您可以创建一个 Intent，对您想要执行的操作进行描述，系统会从其他应用启动相应的 Activity。
	 * 如果有多个 Activity 可以处理 Intent，则用户可以选择要使用哪一个。例如，如果您想允许用户发送电子邮件消息
	 */
	fun gotoSendMailActivity(view: View) {
		var editText = findViewById<EditText>(R.id.editText)

		var userInputText = editText.text

		val intent = Intent(Intent.ACTION_SEND).apply {
			type = "message/rfc822";

			putExtra(Intent.EXTRA_EMAIL, arrayOf("ysh1059@163.com"))
			putExtra(Intent.EXTRA_SUBJECT, "send email test");
			putExtra(Intent.EXTRA_TEXT, userInputText);
		}

		startActivity(intent)
	}

	/**
	 * 新启动的 Activity 不需要返回结果，则当前 Activity 可以通过调用 startActivity() 方法来启动它。
	 */
	fun gotoExampleActivity(view: View) {
		val intent = Intent(this, ExampleActivity::class.java)

		startActivity(intent)
	}

	/**
	 * 使用 onSaveInstanceState() 保存简单轻量的界面状态
	 *
	 * 当您的 Activity 开始停止时，系统会调用 onSaveInstanceState() 方法，以便您的 Activity 可以将状态信息保存到实例状态 Bundle 中。
	 * 此方法的默认实现保存有关 Activity 视图层次结构状态的瞬态信息，例如 EditText 微件中的文本或 ListView 微件的滚动位置。
	 *
	 * 如要保存 Activity 的其他实例状态信息，您必须替换 onSaveInstanceState()，并将键值对添加到您的 Activity 意外销毁时所保存的 Bundle 对象中。
	 * 替换 onSaveInstanceState() 时，如果您希望默认实现保存视图层次结构的状态，则必须调用父类实现。
	 *
	 * 请注意：当用户显式关闭 Activity 时，或者在其他情况下调用 finish() 时，系统不会调用 onSaveInstanceState()。
	 *
	 * 如要保存持久化数据（例如用户首选项或数据库中的数据），您应在 Activity 位于前台时抓住合适机会。
	 * 如果没有这样的时机，您应在执行 onStop() 方法期间保存此类数据。
	 *
	 * 应始终调用 onRestoreInstanceState() 的父类实现，以便默认实现可以恢复视图层次结构的状态。
	 */
	override fun onSaveInstanceState(outState: Bundle) {
		var editText = findViewById<EditText>(R.id.editText)

		var userInputText = editText.text

		Log.d("MainActivity", "user input text is $userInputText")

		outState?.run {
			Log.d("MainActivity", "outState store $userInputText")

			putString(USER_INPUT, userInputText.toString())
		}

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState)
	}

	/**
	 *  使用保存的实例状态恢复 Activity 界面状态
	 *
	 *  重建之前遭到销毁的 Activity 后，您可以从系统传递给 Activity 的 Bundle 中恢复保存的实例状态。
	 *  onCreate() 和 onRestoreInstanceState() 回调方法均会收到包含实例状态信息的相同 Bundle。
	 *
	 *  因为无论系统是新建 Activity 实例还是重新创建之前的实例，都会调用 onCreate() 方法，所以在尝试读取之前，您必须检查状态 Bundle 是否为 null。
	 *  如果为 null，系统将新建 Activity 实例，而不会恢复之前销毁的实例。
	 *
	 *  可以选择实现系统在 onStart() 方法之后调用的 onRestoreInstanceState()，而不是在 onCreate()期间恢复状态。
	 *  仅当存在要恢复的已保存状态时，系统才会调用 onRestoreInstanceState()，因此您无需检查 Bundle 是否为 null：
	 */
	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)

		// Restore state members from saved instance
		savedInstanceState?.run {
			var userInputText = getString(USER_INPUT)

			Log.d("MainActivity", "onRestoreInstanceState user before input text is $userInputText")
		}
	}

	/**
	 * create views and bind data to lists here
	 *
	 * Most importantly, this is where you must call setContentView() to define the layout for the activity's user interface.
	 *
	 * When onCreate() finishes, the next callback is always onStart().
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		Log.d("MainActivity", "bundle is $savedInstanceState")

		Log.d("MainActivity", "the activity is going to create")

		// Always call the superclass first
		super.onCreate(savedInstanceState)

		// Check whether we're recreating a previously destroyed instance
		if (null != savedInstanceState) {
			with(savedInstanceState) {
				var userInputText = savedInstanceState.getString(USER_INPUT)

				Log.d("MainActivity", "onCreate user before input text is $userInputText")
			}
		}


		setContentView(R.layout.activity_main)

		Log.d("MainActivity", "the activity has created")
	}

	/**
	 * As onCreate() exits, the activity enters the Started state, and the activity becomes visible to the user.
	 *
	 * This callback contains what amounts to the activity’s final preparations for coming to the foreground and becoming interactive.
	 */
	override fun onStart() {
		Log.d("MainActivity", "the activity's state is start")

		super.onStart()
	}

	/**
	 *  The system invokes this callback just before the activity starts interacting with the user.
	 *
	 *  At this point, the activity is at the top of the activity stack, and captures all user input.
	 *
	 *  Most of an app’s core functionality is implemented in the onResume() method.
	 */
	override fun onResume() {
		Log.d("MainActivity", "the activity's state is resume")

		super.onResume()
	}

	/**
	 * The onPause() callback always follows onResume().
	 *
	 * The system calls onPause() when the activity loses focus and enters a Paused state.
	 *
	 * When the system calls onPause() for your activity, it technically means your activity is still partially visible,
	 * but most often is an indication that the user is leaving the activity, and the activity will soon enter the Stopped or Resumed state.
	 *
	 * An activity in the Paused state may continue to update the UI if the user is expecting the UI to update.
	 *
	 * You should not use onPause() to save application or user data, make network calls, or execute database transactions.
	 *
	 * Once onPause() finishes executing, the next callback is either onStop() or onResume(), depending on what happens after the activity enters the Paused state.
	 */
	override fun onPause() {
		Log.d("MainActivity", "the activity's state is pause")

		super.onPause()
	}

	/**
	 * The system calls onStop() when the activity is no longer visible to the user.
	 *
	 * This may happen because the activity is being destroyed, a new activity is starting, or an existing activity is entering a Resumed state and is covering the stopped activity.
	 *
	 * In all of these cases, the stopped activity is no longer visible at all.
	 *
	 * The next callback that the system calls is either onRestart(),
	 * if the activity is coming back to interact with the user,
	 * or by onDestroy() if this activity is completely terminating.
	 */
	override fun onStop() {
		Log.d("MainActivity", "the activity's state is stop")

		super.onStop()
	}

	/**
	 *  The system invokes this callback when an activity in the Stopped state is about to restart.
	 *
	 *  onRestart() restores the state of the activity from the time that it was stopped.
	 *
	 *  This callback is always followed by onStart().
	 */
	override fun onRestart() {
		Log.d("MainActivity", "the activity's state is restart")

		super.onRestart()
	}

	/**
	 * The system invokes this callback before an activity is destroyed.
	 *
	 * This callback is the final one that the activity receives.
	 *
	 * onDestroy() is usually implemented to ensure that all of an activity’s resources are released when the activity, or the process containing it, is destroyed.
	 */
	override fun onDestroy() {
		Log.d("MainActivity", "the activity's state is destroy")

		super.onDestroy()
	}

	/**
	 * 设置蓝牙可以被其他设备搜索到
	 */
	private fun setBluetoothCanDiscovered() {
		var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

		if (bluetoothAdapter.scanMode !== BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
			startActivity(discoverableIntent)

			callToast("设置蓝牙已可被其他设备搜索到")
		}
	}

	/**
	 * 用户授权选择结果回调
	 */
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

		if (Activity.RESULT_CANCELED == resultCode) {
			callToast("用户拒绝授权")
		} else if (Activity.RESULT_OK == resultCode) {
			callToast("用户授权成功")
		}

		super.onActivityResult(requestCode, resultCode, data)
	}

	/**
	 * 调用系统页面打开蓝牙
	 */
	fun openBluetooth(view: View) {
		var notSupportBluetooth = !isSupportBluetooth()

		if (notSupportBluetooth) {
			return
		}

		var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

		var bluetoothIsOpened = bluetoothAdapter.isEnabled

		if (bluetoothIsOpened) {
			callToast("蓝牙已打开")

			setBluetoothCanDiscovered()

			return
		}

		startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)

		setBluetoothCanDiscovered()
	}

	/**
	 * 关闭蓝牙
	 */
	fun closeBluetooth(view: View) {
		var notSupportBluetooth = !isSupportBluetooth()

		if (notSupportBluetooth) {
			return
		}

		var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

		var bluetoothIsOpened = bluetoothAdapter.isEnabled

		if (bluetoothIsOpened) {
			bluetoothAdapter.disable()

			return
		}

		callToast("蓝牙已关闭")

		return
	}

	/**
	 * 判断设备是否支持蓝牙
	 */
	private fun isSupportBluetooth(): Boolean {
		var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

		if (bluetoothAdapter == null || !packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
			callToast("当前设备不支持蓝牙")

			return false
		}

		return true
	}

	/**
	 * 调用Toast打印提示信息
	 */
	private fun callToast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
	}

	/**
	 * Called when the user taps the Send button
	 */
	fun sendMessage(view: View) {
		// Do something in response to button
		val editText = findViewById<EditText>(R.id.editText)
		val message = editText.text.toString()

		val intent = Intent(this, DisplayMessageActivity::class.java).apply {
			putExtra(EXTRA_MESSAGE, message)
		}

		startActivity(intent)
	}

}
