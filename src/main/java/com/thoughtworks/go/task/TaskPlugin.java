/*
 * Copyright 2017 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.task;

import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.util.Action;

import java.util.Arrays;

import static com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE;
import static com.thoughtworks.go.util.Logging.withLogging;

@Extension
public class TaskPlugin implements GoPlugin {
    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
        return withLogging("From server to plugin", request, new Action<GoPluginApiRequest, GoPluginApiResponse>() {
            @Override
            public GoPluginApiResponse call(GoPluginApiRequest request) {
                if ("configuration".equals(request.requestName())) {
                    return configurationResponse(request);
                } else if ("view".equals(request.requestName())) {
                    return viewResponse(request);
                }
                throw new RuntimeException("Unhandled: " + request);
            }
        });
    }

    private GoPluginApiResponse viewResponse(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"displayValue\": \"MultiPluginTask\",\n" +
                "  \"template\": \"<div class=\\\"form_item_block\\\">\n" +
                "  <label>Username:<span class='asterix'>*</span></label>\n" +
                "  <input ng-model=\\\"user\\\" />\n" +
                "</div>\n\"" +
                "}");
    }

    private GoPluginApiResponse configurationResponse(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"url\": {\n" +
                "    \"default-value\": \"\",\n" +
                "    \"secure\": false,\n" +
                "    \"required\": false\n" +
                "  },\n" +
                "  \"user\": {\n" +
                "    \"default-value\": \"bob\",\n" +
                "    \"secure\": false,\n" +
                "    \"required\": false\n" +
                "  }\n" +
                "}");
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("task", Arrays.asList("1.0"));
    }
}
