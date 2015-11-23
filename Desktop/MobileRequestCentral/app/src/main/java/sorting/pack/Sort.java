package sorting.pack;

import java.util.ArrayList;

public class Sort implements Comparable<ArrayList<String>> {
    ArrayList<String> _arrayList;

    Sort(ArrayList<String> arrayList) {
        super();
        this._arrayList = arrayList;
    }

    public ArrayList<String> get_arrayList() {
        return _arrayList;
    }

    public void set_arrayList(ArrayList<String> _arrayList) {
        this._arrayList = _arrayList;
    }

    @Override
    public int compareTo(ArrayList<String> arrayList) {
        return 0;
    }

}
