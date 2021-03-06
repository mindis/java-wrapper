/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.watson.developer_cloud.speech_to_text.v1.model;

import com.google.gson.GsonBuilder;


public class SpeechAlternative {

	private String transcript;

	/**
	 * Gets the transcript.
	 * 
	 * @return The transcript
	 */
	public String getTranscript() {
		return transcript;
	}

	/**
	 * Sets the transcript.
	 * 
	 * @param transcript
	 *            The transcript
	 */
	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}

	/**
	 * With transcript.
	 * 
	 * @param transcript
	 *            the transcript
	 * @return the alternative
	 */
	public SpeechAlternative withTranscript(String transcript) {
		this.transcript = transcript;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " "
				+ new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

}
