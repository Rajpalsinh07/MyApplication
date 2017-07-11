package login1.myapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rajpalsinh on 5/18/2017.
 */

public class view_complain_Adapter extends RecyclerView.Adapter<view_complain_Adapter.ViewHolder> {

    private List<complain_data> c_data;
    private Context context;

    public view_complain_Adapter(List<complain_data> c_data, Context context) {
        this.c_data = c_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View V= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_complain_card,parent,false);

        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final complain_data c_p = c_data.get(position);
        holder.c_name.setText(c_p.getC_Name());
        holder.c_location.setText(c_p.getLocation());
        holder.c_details.setText(c_p.getDetails());
        holder.c_status.setText(c_p.getStatus());
        Picasso
                .with(context)
                .load("http://192.168.42.165/Smart_Guj/services/" + c_p.getC_Image())
                .into(holder.im);


    }

    @Override
    public int getItemCount() {
        return c_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView c_name,c_location,c_details,c_status;
        public ImageView im;

        public ViewHolder(View itemView) {
            super(itemView);

            c_name= (TextView) itemView.findViewById(R.id.c_name);
            c_location=(TextView)itemView.findViewById(R.id.c_location);
            c_details= (TextView) itemView.findViewById(R.id.c_details);
            c_status=(TextView)itemView.findViewById(R.id.c_status);
            im=(ImageView) itemView.findViewById(R.id.c_img);



        }
    }
}
