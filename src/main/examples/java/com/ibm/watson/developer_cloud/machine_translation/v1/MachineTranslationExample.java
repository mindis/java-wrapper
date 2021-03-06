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
package java.com.ibm.watson.developer_cloud.machine_translation.v1;

import com.ibm.watson.developer_cloud.machine_translation.v1.MachineTranslation;
import com.ibm.watson.developer_cloud.machine_translation.v1.model.Language;

public class MachineTranslationExample {

	public static void main(String[] args) {
		MachineTranslation service = new MachineTranslation();
		service.setUsernameAndPassword("<username>", "<password>");

		String translation = service.translate("The IBM Watson team is awesome",
				Language.ENGLISH, Language.SPANISH);
		System.out.println(translation);
	}
}
