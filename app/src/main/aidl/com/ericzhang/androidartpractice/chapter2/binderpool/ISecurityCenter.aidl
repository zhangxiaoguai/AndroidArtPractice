// ISecurityCenter.aidl
package com.ericzhang.androidartpractice.chapter2.binderpool;

interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);

}
