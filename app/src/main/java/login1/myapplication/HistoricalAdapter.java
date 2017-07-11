package login1.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class HistoricalAdapter extends RecyclerView.Adapter<HistoricalAdapter.MyViewHolder> {

    private List<TModel> historcal;
    private Context context;

    public HistoricalAdapter(List<TModel> historical, Context context) {
        this.historcal = historical;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv, add;
        public ImageView im;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.name);
            add = (TextView) view.findViewById(R.id.address);
            im = (ImageView) view.findViewById(R.id.img);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TModel tml = historcal.get(position);
        holder.tv.setText(tml.getName());
        holder.add.setText(tml.getAddress());

        Log.e(">>>", "http://192.168.42.168/Smart_Guj/images/" + tml.getImage());

        Picasso
                .with(context)
                .load("http://192.168.42.168/Smart_Guj/images/" + tml.getImage())
                .into(holder.im);
    }

    @Override
    public int getItemCount() {
        return historcal.size();
    }
}