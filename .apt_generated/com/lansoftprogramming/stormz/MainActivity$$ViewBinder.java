// Generated code from Butter Knife. Do not modify!
package com.lansoftprogramming.stormz;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.lansoftprogramming.stormz.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230724, "field 'mIconImageView'");
    target.mIconImageView = finder.castView(view, 2131230724, "field 'mIconImageView'");
    view = finder.findRequiredView(source, 2131230728, "field 'mPercipLabel'");
    target.mPercipLabel = finder.castView(view, 2131230728, "field 'mPercipLabel'");
    view = finder.findRequiredView(source, 2131230730, "field 'mSummaryLabel'");
    target.mSummaryLabel = finder.castView(view, 2131230730, "field 'mSummaryLabel'");
    view = finder.findRequiredView(source, 2131230726, "field 'mHumidityLabel'");
    target.mHumidityLabel = finder.castView(view, 2131230726, "field 'mHumidityLabel'");
    view = finder.findRequiredView(source, 2131230722, "field 'mTimeLabel'");
    target.mTimeLabel = finder.castView(view, 2131230722, "field 'mTimeLabel'");
    view = finder.findRequiredView(source, 2131230720, "field 'mTemperatureLabel'");
    target.mTemperatureLabel = finder.castView(view, 2131230720, "field 'mTemperatureLabel'");
  }

  @Override public void unbind(T target) {
    target.mIconImageView = null;
    target.mPercipLabel = null;
    target.mSummaryLabel = null;
    target.mHumidityLabel = null;
    target.mTimeLabel = null;
    target.mTemperatureLabel = null;
  }
}
