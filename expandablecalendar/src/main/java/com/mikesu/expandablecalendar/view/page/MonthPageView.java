package com.mikesu.expandablecalendar.view.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import com.mikesu.expandablecalendar.ExpandableConfig;
import com.mikesu.expandablecalendar.R;
import com.mikesu.expandablecalendar.view.cell.MonthCellView;
import org.joda.time.DateTime;

/**
 * Created by MikeSu on 04/08/16.
 * www.michalsulek.pl
 */

public class MonthPageView extends FrameLayout {

  private GridLayout gridLayout;
  private DateTime pageDate;

  public MonthPageView(Context context) {
    super(context);
    init();
  }

  public MonthPageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MonthPageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    initVariables();
    initViews();
    setSizeToCells();
  }

  private void initVariables() {
  }

  private void initViews() {
    inflate(getContext(), R.layout.month_page_view, this);
    gridLayout = (GridLayout) findViewById(R.id.grid_layout);
  }

  public void setup(DateTime pageDate) {
    this.pageDate = pageDate;
    addCellsToGrid();
    setSizeToCells();
  }

  private void setSizeToCells() {
    for (int i = 0; i < gridLayout.getChildCount(); i++) {
      MonthCellView monthCellView = (MonthCellView) gridLayout.getChildAt(i);
      GridLayout.LayoutParams gridParams = (GridLayout.LayoutParams) monthCellView.getLayoutParams();
      gridParams.height = ExpandableConfig.cellHeight;
      gridParams.width = ExpandableConfig.cellWidth;
    }
  }

  private void addCellsToGrid() {
    DateTime cellDate = pageDate.plusDays(-pageDate.getDayOfWeek());
    for (int r = 0; r < ExpandableConfig.MONTH_ROWS; r++) {
      for (int c = 0; c < ExpandableConfig.COLUMNS; c++) {
        MonthCellView monthCellView = new MonthCellView(getContext());

        GridLayout.LayoutParams cellParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
        monthCellView.setLayoutParams(cellParams);
        monthCellView.setText(String.valueOf(cellDate.getDayOfMonth()));
        monthCellView.setTimeType(getTimeType(cellDate));

        gridLayout.addView(monthCellView);

        cellDate = cellDate.plusDays(1);
      }
    }
  }

  private MonthCellView.TimeType getTimeType(DateTime cellTime) {
    if (cellTime.getMonthOfYear() < pageDate.getMonthOfYear()) {
      return MonthCellView.TimeType.PAST;
    } else if (cellTime.getMonthOfYear() > pageDate.getMonthOfYear()) {
      return MonthCellView.TimeType.FUTURE;
    } else {
      return MonthCellView.TimeType.CURRENT;
    }
  }
}