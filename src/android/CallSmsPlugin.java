package com.vv.plugin.mobile;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

public class CallSmsPlugin extends CordovaPlugin {

	private static final int PHONE_CALL = 0;
	private static final int PHONE_SMS = 1;
	private CallbackContext callbackContext;
	
	@Override
	public boolean execute(String action, JSONArray phone,
			CallbackContext callbackContext) throws JSONException {
		
		this.callbackContext = callbackContext;
		if ("call".equalsIgnoreCase(action)) {
			call(phone.getString(0), callbackContext);
			return true;
		} else if ("sms".equalsIgnoreCase(action)) {
			sms(phone.getString(0), callbackContext);
			return true;
		}
		callbackContext.error("fail");
		return false;
	}
	
	/**
	 * @Title: call
	 * @Description: 拨打电话 
	 * @param phone
	 * @param callbackContext
	 * @return
	 * @throws
	 */
	private boolean call(String phone, CallbackContext callbackContext) {
		if (phone != null && phone.length() > 0) {
			if (PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse(phone));
				this.cordova.startActivityForResult(this, intent, PHONE_CALL);
			} else {
				callbackContext.error(phone + "不是有效的电话号码");
			}
		} else {
			callbackContext.error("电话号码不能为空");
		}
		return true;
	}
	
	/**
	 * @Title: sms
	 * @Description: 发送短信 
	 * @param phone
	 * @param callbackContext
	 * @return
	 * @throws
	 */
	private boolean sms(String phone, CallbackContext callbackContext) {
		if (phone != null && phone.length() > 0) {
			if (PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse(phone));
				this.cordova.startActivityForResult(this, intent, PHONE_SMS);
			} else {
				callbackContext.error(phone + "不是有效的电话号码");
			}
		} else {
			callbackContext.error("电话号码不能为空");
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == Activity.RESULT_OK) {
			if (resultCode == PHONE_CALL) {
				this.callbackContext.success("拨打电话成功");
			} else if (resultCode == PHONE_SMS) {
				this.callbackContext.success("发送短信成功");
			}
		} else if (requestCode == Activity.RESULT_CANCELED) {
			try {
				 if (resultCode != PHONE_CALL && resultCode != PHONE_SMS) {
					 this.callbackContext.success("fail");
				}
			} catch (Exception e) {
				this.callbackContext.error(e.getMessage());
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
}
