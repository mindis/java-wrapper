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
package java.com.ibm.watson.developer_cloud.text_to_speech.v1;

import java.io.FileNotFoundException;

import com.ibm.watson.developer_cloud.WatsonServiceTest;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.VoiceSet;

public class TextToSpeechExample extends WatsonServiceTest {


	public static void main(String[] args) throws FileNotFoundException {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("<username>", "<password>");

		VoiceSet voices = service.getVoices();
		System.out.println(voices);
	}

}