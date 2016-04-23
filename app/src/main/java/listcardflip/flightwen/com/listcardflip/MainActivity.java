package listcardflip.flightwen.com.listcardflip;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<CardInfo> mList = new ArrayList<>();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        addCardInfo();

    }

    private void addCardInfo(){
        for(int i=1;i<=10;i++){
            CardInfo cardInfo = new CardInfo();
            cardInfo.setIcon(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher));
            cardInfo.setInfo("我是卡片正面forward side"+i);
            cardInfo.setMoreInfo("我是卡片背面back side"+i);
            mList.add(cardInfo);
        }

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new CustomListAdapter(mList,context));
    }

    public class CustomListAdapter extends BaseAdapter {
        private List<CardInfo> mList = new ArrayList<>();
        private LayoutInflater mInflater;

        public CustomListAdapter(List<CardInfo> list,Context context) {
            this.mList = list;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mList.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getCount() == 0) {
                return null;
            }

            ViewHolder holder ;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.ivImage = (ImageView) convertView
                        .findViewById(R.id.ivIcon);
                holder.tvString = (TextView) convertView
                        .findViewById(R.id.tv_string);
                holder.tvMoreString = (TextView) convertView
                        .findViewById(R.id.tv_more_string);

                holder.forwardCard = (LinearLayout) convertView
                        .findViewById(R.id.card);
                holder.backCard = (LinearLayout) convertView
                        .findViewById(R.id.card2);
                holder.layout= (RelativeLayout) convertView
                        .findViewById(R.id.layout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.backCard.setVisibility(View.GONE);
                holder.forwardCard.setVisibility(View.VISIBLE);
                holder.forwardCard.requestFocus();
            }

            final ViewHolder finalHolder = holder;

            holder.forwardCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFlipAnimation(finalHolder.layout, finalHolder.forwardCard, finalHolder.backCard);

                }
            });

            holder.backCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFlipAnimation(finalHolder.layout, finalHolder.backCard, finalHolder.forwardCard);
                }
            });


            CardInfo cardInfo = mList.get(position);
            holder.ivImage.setImageBitmap(cardInfo.getIcon());
            holder.tvString.setText(cardInfo.getInfo());
            holder.tvMoreString.setText(cardInfo.getMoreInfo());

            return convertView;
        }

    }
    public static class ViewHolder {
        private RelativeLayout layout;
        private LinearLayout forwardCard;
        private LinearLayout backCard;
        private ImageView ivImage;
        private TextView tvString;
        private TextView tvMoreString;

    }

    private void startFlipAnimation(final View listItemView,final View cardView,final View cardView2){
        AnimationSet animationSet = new AnimationSet(true);

        int pivotX = listItemView.getWidth() / 2;
        int pivotY = listItemView.getHeight() / 2;


        ScaleAnimation sa = new ScaleAnimation(1f, 0f, 1f, 1f, pivotX,
                pivotY);

        sa.setDuration(200);
        sa.setAnimationListener(new ScaletoView(cardView, cardView2, listItemView));

        animationSet.addAnimation(sa);
        animationSet.start();
        listItemView.startAnimation(animationSet);
    }

    public class ScaletoView implements Animation.AnimationListener {
        View card;
        View card2;
        View listItemView;

        public ScaletoView(View card,View card2,View listItemView){
            this.card = card;
            this.card2 = card2;
            this.listItemView = listItemView;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {

            int pivotX = listItemView.getWidth() / 2;
            int pivotY = listItemView.getHeight() / 2;

            ScaleAnimation sa = new ScaleAnimation(0f, 1f, 1f, 1f, pivotX,
                    pivotY);


            card.setVisibility(View.GONE);
            card2.setVisibility(View.VISIBLE);
            card2.requestFocus();


            sa.setDuration(200);
            sa.setFillAfter(true);
            sa.setInterpolator(new AccelerateInterpolator());
            listItemView.startAnimation(sa);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

    }
}
