/**
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid.test.espresso.intro;

import android.support.test.runner.AndroidJUnit4;

import org.catrobat.paintroid.R;
import org.catrobat.paintroid.test.espresso.intro.util.WelcomeActivityIntentsTestRule;
import org.catrobat.paintroid.test.espresso.util.IntroUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.catrobat.paintroid.test.espresso.util.UiMatcher.checkDotsColors;
import static org.catrobat.paintroid.test.espresso.util.UiMatcher.equalsNumberDots;
import static org.catrobat.paintroid.test.espresso.util.UiMatcher.isNotVisible;
import static org.catrobat.paintroid.test.espresso.util.UiMatcher.isOnLeftSide;
import static org.catrobat.paintroid.test.espresso.util.UiMatcher.isOnRightSide;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RTLTest {

	@Rule
	public WelcomeActivityIntentsTestRule activityRule = new WelcomeActivityIntentsTestRule(false, true);

	@Test
	public void testNumberDots() {
		onView(withId(R.id.pocketpaint_layout_dots))
				.check(matches(equalsNumberDots(activityRule.getLayouts().length)));
	}

	@Test
	public void clickSkip() {
		onView(withId(R.id.pocketpaint_btn_skip))
				.check(matches(isDisplayed()))
				.perform(click());
		assertTrue(activityRule.getActivity().isFinishing());
	}

	@Test
	public void testCheckLastPage() {
		IntroUtils.changeIntroPage(IntroUtils.getPageIndexFromLayout(activityRule.getLayouts(), R.layout.pocketpaint_slide_intro_getstarted));
		onView(withId(R.id.pocketpaint_btn_skip))
				.check(isNotVisible());
		onView(withId(R.id.pocketpaint_btn_next))
				.check(matches(isCompletelyDisplayed()))
				.check(matches(withText(R.string.lets_go)))
				.perform(click());

		assertTrue(activityRule.getActivity().isFinishing());
	}

	@Test
	public void testButtonsCompleteVisible() {
		for (int i = activityRule.getLayouts().length - 1; i < 0; i--) {
			IntroUtils.changeIntroPage(i);
			onView(withId(R.id.pocketpaint_btn_skip))
					.check(matches(isCompletelyDisplayed()))
					.check(matches(withText(R.string.next)));
			onView(withId(R.id.pocketpaint_btn_next))
					.check(matches(isCompletelyDisplayed()))
					.check(matches(withText(R.string.skip)));
		}
	}

	@Test
	public void testCheckDotsColor() {
		int colorActive = activityRule.getColorActive();
		int colorInactive = activityRule.getColorInactive();

		for (int i = activityRule.getLayouts().length - 1; i < 0; i--) {
			IntroUtils.changeIntroPage(i);
			onView(withId(R.id.pocketpaint_layout_dots))
					.check(matches(checkDotsColors(i, colorActive, colorInactive)));
		}
	}

	@Test
	public void isSkipButtonOppositeSide() {
		onView(withId(R.id.pocketpaint_btn_skip))
				.check(matches(isOnRightSide()));
	}

	@Test
	public void isNextButtonOppositeSide() {
		onView(withId(R.id.pocketpaint_btn_next))
				.check(matches(isOnLeftSide()));
	}
}
