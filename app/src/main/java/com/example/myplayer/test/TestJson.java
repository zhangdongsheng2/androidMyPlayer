package com.example.myplayer.test;

public class TestJson {

    public int id;
    public String name;
    public String parent;

    @Override
    public String toString() {
        return "TestJson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }

//   复杂json解析
//    try {
//        String testJson = FileUtil.ReadFile(getActivity().getAssets().open("testJson"));
//        List<TestJson> list_person = GsonTools.changeGsonToList(testJson, TestJson.class);
//
//        KLog.e(testJson);
//        KLog.e(list_person);
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}