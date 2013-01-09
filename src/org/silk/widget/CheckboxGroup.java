package org.silk.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * The CheckboxGroup is a custom component to create multiple textboxes with checkboxes.
 * This is not a very configurable option, but it uses less space than the normal ListView
 * approach.
 * <p>
 * This class may be deprecated in the future, and ListView approach may be taken
 * </p>
 * @author varun
 *
 */
public class CheckboxGroup extends RelativeLayout {

	private final int CHECKBOX_TICK_ID = 128;
	private final int CHECKBOX_TEXT_ID = 129;
	
	private Context context;
	int currentCheckbox = 1;

	/**
	 * Constructor for CheckboxGroup
	 * @param context
	 */
	public CheckboxGroup(Context context) {
		super(context);
		this.context = context;
	}
	
	/**
	 * Adds a checkbox item to the group
	 * @param text The item string
	 */
	public void addCheckbox(String text) {
		RelativeLayout checkbox = new RelativeLayout(context);
		RelativeLayout.LayoutParams checkboxLayout = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		checkbox.setId(currentCheckbox);
		
		CheckBox cb = new CheckBox(context);
		RelativeLayout.LayoutParams cbLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		cb.setId(CHECKBOX_TICK_ID);
		checkbox.addView(cb, cbLayout);
		
		TextView tv = new TextView(context);
		tv.setText(text);
		tv.setTextSize(20);
		RelativeLayout.LayoutParams tvLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv.setId(CHECKBOX_TEXT_ID);
		tvLayout.addRule(RelativeLayout.ALIGN_BOTTOM, CHECKBOX_TICK_ID);
		tvLayout.addRule(RelativeLayout.RIGHT_OF, CHECKBOX_TICK_ID);
		int scale = (int) this.getResources().getDisplayMetrics().density;
		int margin = (int) (scale * 10 + 0.5);
		tvLayout.bottomMargin = margin;
		checkbox.addView(tv, tvLayout);
		
		if (currentCheckbox > 1) {
			checkboxLayout.addRule(RelativeLayout.BELOW, currentCheckbox-1);
		}
		this.addView(checkbox, checkboxLayout);
		currentCheckbox++;
	}
	
	/**
	 * @return Returns the checked items from the group
	 */
	public List<String> getCheckedItems() {
		List<String> checkedItems = new ArrayList<String>();
		int numChildren = this.getChildCount();
		RelativeLayout child;
		CheckBox childCheckBox;
		for (int i=0; i<numChildren; i++) {
			child = (RelativeLayout) this.getChildAt(i);
			childCheckBox = getCheckboxFromItem(child);
			if (childCheckBox == null)
				continue;
			if (childCheckBox.isChecked())
				checkedItems.add(getTextFromItem(child));
		}
		return checkedItems;
	}

	private String getTextFromItem(RelativeLayout child) {
		int count = child.getChildCount();
		for (int i=0; i<count; i++) {
			if (child.getChildAt(i) instanceof TextView && !(child.getChildAt(i) instanceof CheckBox))
				return ((TextView) child.getChildAt(i)).getText().toString();
		}
		return null;
	}

	private CheckBox getCheckboxFromItem(RelativeLayout child) {
		int count = child.getChildCount();
		for (int i=0; i<count; i++) {
			if (child.getChildAt(i) instanceof CheckBox)
				return (CheckBox) child.getChildAt(i);
		}
		return null;
	}

}