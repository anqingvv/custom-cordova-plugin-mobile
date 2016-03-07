
var exec = require('cordova/exec');
var mobile = {};

/** 打电话 */
mobile.call = function(successCallback, errorCallback, phone) {
	exec(successCallback, errorCallback, "CustomCordovaPlugin", "call", [phone]);
};

/** 发短信 */
mobile.sms = function(successCallback, errorCallback, phone) {
	exec(successCallback, errorCallback, "CustomCordovaPlugin", "sms", [phone]);
};

module.exports = mobile;
