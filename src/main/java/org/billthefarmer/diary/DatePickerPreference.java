////////////////////////////////////////////////////////////////////////////////
//
//  Diary - Personal diary for Android
//
//  Copyright © 2017  Bill Farmer
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.billthefarmer.diary;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

// DatePickerPreference
public class DatePickerPreference extends DialogPreference
{
    private final static int DEFAULT_VALUE = 1;
    private DatePicker picker;
    private long value;

    // Constructor
    public DatePickerPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    // On create dialog view
    @Override
    protected View onCreateDialogView()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);

        picker = new DatePicker(getContext());
        picker.init(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE),
                    new DatePicker.OnDateChangedListener()
                    {
                        // onDateChanged
                        public void onDateChanged (DatePicker view, 
                                                   int year, 
                                                   int monthOfYear, 
                                                   int dayOfMonth)
                        {
                            Calendar calendar = new
                                GregorianCalendar(year, monthOfYear,
                                                  dayOfMonth);
                            value = calendar.getTimeInMillis();
                        }
                    });
        return picker;
    }

    // On get default value
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getInteger(index, DEFAULT_VALUE);
    }

    // On set initial value
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue,
                                     Object defaultValue)
    {
        if (restorePersistedValue)
        {
            // Restore existing state
            value = getPersistedLong(0);
        }

        else
        {
            // Set default state from the XML attribute
            value = (Long) defaultValue;
            persistLong(value);
        }
    }

    // On dialog closed
    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        // When the user selects "OK", persist the new value
        if (positiveResult)
        {
            persistLong(value);
        }
    }

    // Get value
    protected long getValue()
    {
        return value;
    }
}
