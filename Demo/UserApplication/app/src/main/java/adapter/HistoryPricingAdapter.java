package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swomfire.vehicleNFCUser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.HourHasPrice;
import model.OrderPricing;
import service.UserService;

public class HistoryPricingAdapter extends RecyclerView.Adapter<HistoryPricingAdapter.MyViewHolder> {
    private List<HourHasPrice> hourHasPrices;
    private long checkInDate, checkOutDate;
    private int minutes;

    public HistoryPricingAdapter(List<HourHasPrice> hourHasPrices, int minutes, long checkInDate, long checkOutDate) {
        this.minutes = minutes;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.hourHasPrices = new ArrayList<>();
        for (HourHasPrice hourHasPrice : hourHasPrices) {
            if (this.hourHasPrices.size() < 1) {
                hourHasPrice.setTotal(hourHasPrice.getPrice());
                this.hourHasPrices.add(hourHasPrice);
            } else {
                HourHasPrice tmp = this.hourHasPrices.get(this.hourHasPrices.size() - 1);
                if ((Double.compare(tmp.getPrice(), hourHasPrice.getPrice()) == 0)) {
                    if (tmp.getTotal() == null) {
                        tmp.setTotal(hourHasPrice.getPrice());
                    } else {
                        tmp.setTotal(tmp.getTotal() + hourHasPrice.getPrice());
                    }
                } else {
                    hourHasPrice.setTotal(hourHasPrice.getPrice());
                    this.hourHasPrices.add(hourHasPrice);
                }
            }
        }
        this.hourHasPrices = HourHasPrice.sortList(this.hourHasPrices);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_history_pricing, parent, false);
        return new MyViewHolder(itemView);
    }

    private int passHour = 0;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HourHasPrice hourHasPrice = hourHasPrices.get(position);
//        if (position == 0) {
//            passHour = hourHasPrice.getHour();
//            fromHour = hourHasPrice.getHour() + " giờ đầu";
//        } else if (position == hourHasPrices.size() - 1) {
//            hourHasPrice.setHour(hourHasPrice.getHour() - passHour);
//            String minutesPass = (minutes != 0) ? " " + minutes + " phút" : "";
//            fromHour = hourHasPrice.getHour() + " giờ" + minutesPass + " sau";
//            hourHasPrice.setTotal(hourHasPrice.getTotal() + (((double) minutes / 60) * hourHasPrice.getPrice()));
//        } else {
//            fromHour = "giờ " + (passHour + 1) + " đến " + hourHasPrice.getHour();
//            passHour = hourHasPrice.getHour();
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        long mili = UserService.convertToMilliseconds(hourHasPrice.getHour() - passHour, true);
        if (position == hourHasPrices.size() - 1) {
            mili += UserService.convertToMilliseconds(minutes, false);
        }
        String toHour = "";
        if (!UserService.compareTwoDate(checkInDate, checkInDate + mili)) {
            SimpleDateFormat extra = new SimpleDateFormat("HH:mm dd/MM");
            toHour = extra.format(checkInDate + mili);
        } else {
            toHour = sdf.format(checkInDate + mili);
        }
//        String fromHour = (orderPricing.getFromHour() == 0) ? "Giờ đầu" : "Từ " + orderPricing.getFromHour() + " giờ";
        holder.txtFrom.setText(sdf.format(checkInDate));
        holder.txtTo.setText(toHour);
        holder.txtTotal.setText(UserService.convertMoney(hourHasPrice.getPrice()) + " x " + (hourHasPrice.getHour() - passHour));
        checkInDate += mili;
        passHour = hourHasPrice.getHour();

//        holder.txtFee.setText(UserService.convertMoney(hourHasPrice.g()));
    }

    @Override
    public int getItemCount() {
        return hourHasPrices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFrom, txtTo, txtTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtFrom = itemView.findViewById(R.id.txtFrom);
            txtTo = itemView.findViewById(R.id.txtTo);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }
    }


}
