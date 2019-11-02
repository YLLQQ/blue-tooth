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

class MainActivity : AppCompatActivity() {

	/**
	 * create views and bind data to lists here
	 *
	 * Most importantly, this is where you must call setContentView() to define the layout for the activity's user interface.
	 *
	 * When onCreate() finishes, the next callback is always onStart().
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		Log.d("MainActivity", "the activity is going to create")

		super.onCreate(savedInstanceState)
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
