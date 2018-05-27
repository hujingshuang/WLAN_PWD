package com.swjtu;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class WLAN {
    private final static String path = "D:\\wlan_pwd\\";
    private List<String> wlanList;

    public WLAN() {
        wlanList = new ArrayList<>();
    }

    public String[][] getInfo() {
        String[][] wlanInfo = null;
        try {
            getWlan();
            saveWlan();
            wlanInfo = getWlanInfo();
            deleteWlan();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wlanInfo;
    }

    private boolean getWlan() throws IOException {
        Process pro = Runtime.getRuntime().exec("cmd /c netsh wlan show profile");
        BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));

        String msg;
        while ((msg = br.readLine()) != null) {
            if (msg.contains("所有用户配置文件 : ")) {
                wlanList.add(msg.split("所有用户配置文件 : ")[1]);
            }
        }
        br.close();
        return !wlanList.isEmpty();
    }

    private void saveWlan() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        Runtime.getRuntime().exec("cmd /c netsh wlan export profile folder=" + path + " key=clear");
    }

    private String[][] getWlanInfo() throws IOException {
        String[][] wlanInfo = new String[wlanList.size()][2];

        int idx = 0;
        for (String wlanName : wlanList) {
            BufferedReader br = new BufferedReader(new FileReader(path + "WLAN-" + wlanName + ".xml"));
            String str;
            while ((str = br.readLine()) != null) {
                if (str.contains("keyMaterial")) {
                    String res = str.split("keyMaterial")[1];
                    wlanInfo[idx][0] = wlanName;
                    wlanInfo[idx][1] = res.substring(1, res.length() - 2);
                    break;
                }
            }
            idx++;
            br.close();
        }
        return wlanInfo;
    }

    private void deleteWlan() {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            }
        }
        dirFile.delete();
    }
}
