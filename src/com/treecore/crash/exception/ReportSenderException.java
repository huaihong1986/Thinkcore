/*
 *  Copyright 2010 Kevin Gaudin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.treecore.crash.exception;

/**
 * 异常处理
 */
@SuppressWarnings("serial")
public class ReportSenderException extends Exception {

	/**
	 * Creates a new {@link ReportSenderException} instance. You can provide a
	 * detailed message to explain what went wrong.
	 * 
	 * @param detailMessage
	 *            A message to explain the cause of this exception.
	 * @param throwable
	 *            An optional throwable which caused this Exception.
	 */
	public ReportSenderException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
