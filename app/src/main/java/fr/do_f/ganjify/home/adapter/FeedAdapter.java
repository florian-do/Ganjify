package fr.do_f.ganjify.home.adapter;

import android.app.ActionBar;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.api.json.StatsResponse;
import fr.do_f.ganjify.utils.Utils;

/**
 * Created by do_f on 23/02/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<StatsResponse> items;

    public FeedAdapter(List<StatsResponse> response)
    {
        items = response;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_feed, parent, false);
        CellFeedViewHolder cellFeedViewHolder = new CellFeedViewHolder(view);
        return cellFeedViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CellFeedViewHolder) holder).bindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public synchronized void refreshAdapter(List<StatsResponse> _items) {
        items.clear();
        items.addAll(_items);
        notifyDataSetChanged();
    }

    public class CellFeedViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.feed_type)
        ImageView   type;

        @Bind(R.id.feed_title)
        TextView    title;

        @Bind(R.id.feed_time)
        TextView    time;

        @Bind(R.id.feed_comment)
        TextView    comment;

        @Bind(R.id.feed_snoop)
        ImageView   snoop;

        public CellFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(StatsResponse item)
        {
            String _title = item.getUser().getUsername();
            String _friend = "";
            String _time = item.getCreated().replace("T", " ");
            String _comment = (item.getComment() == null) ? "" : item.getComment();
            _time = _time.substring(0, _time.indexOf("."));
            String _hour = _time.split(" ")[1];

            _title += " "+ (item.getType().equals("PET")
                    ? "a fumé un gros cône"
                    : "a coulé une douille");

            for (int i = 0; i < item.getFriends().size(); i++)
            {
                _friend += (i == 0) ? " avec " : "";
                _friend += item.getFriends().get(i).getUsername();
                _friend += (i+1 == item.getFriends().size()) ? "" : ", ";
            }

            _title += _friend;

            type.setImageResource((item.getType().equals("PET")) ? R.drawable.feed_type_spliff : R.drawable.feed_type_bong);
            title.setText(_title);
            time.setText(_time);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (_comment.isEmpty())
            {
                lp.setMargins(0, -25, 0, 0);
                comment.setLayoutParams(lp);
                comment.setText("");
            }
            else
            {
                lp.setMargins(0, 5, 0, 0);
                comment.setLayoutParams(lp);
                comment.setText(_comment);
            }

            if (_hour.indexOf("15:20") == -1 && _hour.indexOf("03:20") == -1)
                snoop.setBackground(null);
            else {
                snoop.setBackgroundResource(R.drawable.small_snoop_dogg);
            }
        }
    }
}
