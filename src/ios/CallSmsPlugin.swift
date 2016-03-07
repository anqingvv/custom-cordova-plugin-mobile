//
//  CallSmsPlugin.swift
//  CommonComponent
//
//  Created by LiVV on 16/3/2.
//
//

import Foundation

@objc(CallSmsPlugin) class CallSmsPlugin: CDVPlugin {
    
    /** call **/
    func call(command: CDVInvokedUrlCommand) {
        var phoneNum = command.arguments[0] as string;
        UIApplication.sharedApplication().openURL(NSURL(string: "tel:" + phoneNum));
    }
    
    /** sms **/
    func sms(command: CDVInvokedUrlCommand) {
        var phoneNum = command.arguments[0] as string;
        UIApplication.sharedApplication().openURL(NSURL(string: "sms:" + phoneNum));
    }
}