package com.thinkcore.utils.task;

import java.util.Timer;
import java.util.TimerTask;

public class TTimerTask {
	private ITimerListener listener;
	private long delayMs;
	private Timer timer;
	private TimerTask timerTask;
	private boolean isStarted = false;
	private TTimerTask that;

	/**
	 * 构造函数
	 * 
	 * @param delayMs
	 *            延时
	 * @param listen
	 *            定时处理器，由调用者定制实现
	 */
	public TTimerTask(int delayMs, ITimerListener listen) {
		this.listener = listen;
		this.delayMs = delayMs;
		this.that = this;
	}

	/**
	 * 启动定时器
	 */
	public void startTimer(boolean bFlag) {
		stopTimer();
		timer = new Timer(true);
		timerTask = new TimerTask() {

			@Override
			public void run() {
				if (null != listener && isRunning()) {
					listener.onTimerListen(that);
				}
			}

		};
		if (bFlag) {
			timer.schedule(timerTask, 0, delayMs);
		} else {
			timer.schedule(timerTask, delayMs);
		}
		isStarted = true;
	}

	/**
	 * 停止定时器
	 */
	public void stopTimer() {
		if (null != timer) {
			timer.cancel();
			timer = null;
		}
		if (null != timerTask) {
			timerTask.cancel();
			timerTask = null;
		}
		isStarted = false;
	}

	public boolean isRunning() {
		return isStarted;
	}

	public interface ITimerListener {
		public abstract void onTimerListen(TTimerTask task);
	}
}
