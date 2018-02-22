// IMyAidlInterface.aidl
package com.example.remotserviceapp;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * 处理购房业务
     * dealMoney 业务费用
     * yezhu 业主
     * maijia 买家
     */
   String deal(int dealMoney, String yezhu,String maijia);
}
