package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swomfire.vehicleNFCUser.R;

import java.util.List;

import model.OrderPricing;

public class OrderPricingAdapter extends RecyclerView.Adapter<OrderPricingAdapter.MyViewHolder> {
    private List<OrderPricing> orderPricings;

    public OrderPricingAdapter(List<OrderPricing> orderPricings) {
        this.orderPricings = orderPricings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_order_pricing, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderPricing orderPricing = orderPricings.get(position);
        holder.txtFrom.setText("Từ " + orderPricing.getFromHour()+" giờ");
        holder.txtPrice.setText((long)(orderPricing.getPricePerHour()*1000)+" đ");
    }

    @Override
    public int getItemCount() {
        return orderPricings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFrom, txtPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtFrom = itemView.findViewById(R.id.txtFrom);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }


}
