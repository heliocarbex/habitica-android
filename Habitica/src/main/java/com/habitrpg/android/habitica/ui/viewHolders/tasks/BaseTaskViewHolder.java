package com.habitrpg.android.habitica.ui.viewHolders.tasks;

import com.habitrpg.android.habitica.R;
import com.habitrpg.android.habitica.events.TaskTappedEvent;
import com.habitrpg.android.habitica.ui.helpers.MarkdownParser;
import com.magicmicky.habitrpgwrapper.lib.models.tasks.Task;

import org.greenrobot.eventbus.EventBus;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected Context context;

    public Task task;

    @BindView(R.id.checkedTextView)
    TextView titleTextView;

    @BindView(R.id.notesTextView)
    TextView notesTextView;

    @Nullable
    @BindView(R.id.rightBorderView)
    View rightBorderView;

    @BindColor(R.color.task_gray)
    int taskGray;

    public BaseTaskViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        itemView.setClickable(true);

        ButterKnife.bind(this, itemView);

        //Re enable when we find a way to only react when a link is tapped.
        //this.notesTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //this.titleTextView.setMovementMethod(LinkMovementMethod.getInstance());

        context = itemView.getContext();
    }

    public void bindHolder(Task newTask, int position) {
        this.task = newTask;
        this.titleTextView.setText(MarkdownParser.parseMarkdown(this.task.getText()));
        if (this.task.getNotes().length() > 0) {
            this.notesTextView.setText(MarkdownParser.parseMarkdown(this.task.getNotes()));
            this.notesTextView.setVisibility(View.VISIBLE);
        } else {
            this.notesTextView.setVisibility(View.GONE);
        }

        if (this.rightBorderView != null) {
            this.rightBorderView.setBackgroundResource(this.task.getLightTaskColor());
        }
    }

    @Override
    public void onClick(View v) {
        if (v != itemView) {
            return;
        }

        TaskTappedEvent event = new TaskTappedEvent();
        event.Task = task;

        EventBus.getDefault().post(event);
    }
}
