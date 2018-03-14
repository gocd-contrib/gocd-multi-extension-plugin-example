/*
 * Copyright 2018 ThoughtWorks, Inc.
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

package com.thoughtworks.go.analytics;

import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.util.Action;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

import static com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE;
import static com.thoughtworks.go.util.Logging.withLogging;

@Extension
public class AnalyticsPlugin implements GoPlugin {
    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("analytics", Arrays.asList("1.0"));
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
        return withLogging("Analytics plugin: Server to plugin", request, new Action<GoPluginApiRequest, GoPluginApiResponse>() {
            @Override
            public GoPluginApiResponse call(GoPluginApiRequest request) {
                /* Uncomment the next few lines to make the plugin fail, because more than one extension will support configuration. */
//                if ("go.plugin-settings.get-view".equals(request.requestName())) {
//                    return getView(request);
//                }
//                if ("go.plugin-settings.get-configuration".equals(request.requestName())) {
//                    return getConfiguration(request);
//                }
                if ("go.cd.analytics.get-capabilities".equals(request.requestName())) {
                    return capabilities(request);
                }
                if ("go.cd.analytics.get-icon".equals(request.requestName())) {
                    return icon(request);
                }
                if ("go.cd.analytics.get-static-assets".equals(request.requestName())) {
                    return assets(request);
                }
                throw new RuntimeException("Unhandled: " + request);
            }
        });
    }

    private GoPluginApiResponse getView(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"template\": \"<div>some html for analytics plugin</div>\"\n" +
                "}");
    }

    private GoPluginApiResponse getConfiguration(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"analytics1\": {\n" +
                "    \"default-value\": \"\",\n" +
                "    \"secure\": false,\n" +
                "    \"required\": false\n" +
                "  },\n" +
                "  \"analytics2\": {\n" +
                "    \"default-value\": \"bob\",\n" +
                "    \"secure\": false,\n" +
                "    \"required\": false\n" +
                "  }\n" +
                "}");
    }

    private GoPluginApiResponse capabilities(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"supports_pipeline_analytics\": true,\n" +
                "  \"supports_analytics_dashboard\": true\n" +
                "}\n");
    }

    private GoPluginApiResponse icon(GoPluginApiRequest request) {
        return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{\n" +
                "  \"content_type\": \"image/svg+xml\",\n" +
                "  \"data\": \"" + Base64.getEncoder().encodeToString("<svg></svg>".getBytes()) + "\"\n" +
                "}\n");
    }

    private GoPluginApiResponse assets(GoPluginApiRequest request) {
        try {
            byte[] assetsZip = IOUtils.toByteArray(this.getClass().getResourceAsStream("/assets.zip"));
            return new DefaultGoPluginApiResponse(SUCCESS_RESPONSE_CODE, "{"
                    + "  \"assets\": \"" + Base64.getEncoder().encodeToString(assetsZip) + "\"\n" +
                    "}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
