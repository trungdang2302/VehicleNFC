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
import service.UserService;

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
        String fromHour = (orderPricing.getFromHour() == 0) ? "Giờ đầu" : "Từ " + orderPricing.getFromHour() + " giờ";
        holder.txtFrom.setText(fromHour);
        holder.txtPrice.setText(UserService.convertMoney(orderPricing.getPricePerHour()));
        holder.txtFee.setText(UserService.convertMoney(orderPricing.getLateFeePerHour()));
    }

    @Override
    public int getItemCount() {
        return orderPricings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFrom, txtPrice, txtFee;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtFrom = itemView.findViewById(R.id.txtFrom);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtFee = itemView.findViewById(R.id.txtFee);
        }
    }


}
