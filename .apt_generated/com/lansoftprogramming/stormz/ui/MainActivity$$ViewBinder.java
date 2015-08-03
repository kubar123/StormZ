// Generated code from Butter Knife. Do not modify!
package com.lansoftprogramming.stormz.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.lansoftprogramming.stormz.ui.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230727, "field 'mHumidityValue'");
    target.mHumidityValue = finder.castView(view, 2131230727, "field 'mHumidityValue'");
    view = finder.findRequiredView(source, 2131230722, "field 'mTimeLabel'");
    target.mTimeLabel = finder.castView(view, 2131230722, "field 'mTimeLabel'");
    view = finder.findRequiredView(source, 2131230724, "field 'mIconImageView'");
    target.mIconImageView = finder.castView(view, 2131230724, "field 'mIconImageView'");
    view = finder.findRequiredView(source, 2131230732, "field 'mProgressBar1'");
    target.mProgressBar1 = finder.castView(view, 2131230732, "field 'mProgressBar1'");
    view = finder.findRequiredView(source, 2131230723, "field 'mLocationLabel'");
    target.mLocationLabel = finder.castView(view, 2131230723, "field 'mLocationLabel'");
    view = finder.findRequiredView(source, 2131230730, "field 'mSummaryValue'");
    target.mSummaryValue = finder.castView(view, 2131230730, "field 'mSummaryValue'");
    view = finder.findRequiredView(source, 2131230729, "field 'mPercipValue'");
    target.mPercipValue = finder.castView(view, 2131230729, "field 'mPercipValue'");
    view = finder.findRequiredView(source, 2131230720, "field 'mTemperatureLabel'");
    target.mTemperatureLabel = finder.castView(view, 2131230720, "field 'mTemperatureLabel'");
    view = finder.findRequiredView(source, 2131230731, "field 'mRefreshImageView'");
    target.mRefreshImageView = finder.castView(view, 2131230731, "field 'mRefreshImageView'");
  }

  @Override public void unbind(T target) {
    target.mHumidityValue = null;
    target.mTimeLabel = null;
    target.mIconImageView = null;
    target.mProgressBar1 = null;
    target.mLocationLabel = null;
    target.mSummaryValue = null;
    target.mPercipValue = null;
    target.mTemperatureLabel = null;
    target.mRefreshImageView = null;
  }
}
