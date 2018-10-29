package local.hal.st32.android.asahifeedreader60213;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RowItemAdapter extends ArrayAdapter {

    private LayoutInflater layoutinflater;

    // コンストラクタ
    public RowItemAdapter(Context context, int textViewResourceId, List<Item> objects){
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 指定行のデータを取得
        Item detail = (Item)getItem(position);

        // nullの場合のみ再作成
        if(null == convertView){
            convertView = layoutinflater.inflate(R.layout.activity_feed_list_detail, null);
        }

        // 行のデータを項目へ設定
        TextView text1 = (TextView)convertView.findViewById(R.id.textView);
        text1.setText(detail.getTitle());

        TextView text2 = (TextView)convertView.findViewById(R.id.textView2);
        text2.setText(detail.getLink());

        // 返却
        return convertView;
    }

}
