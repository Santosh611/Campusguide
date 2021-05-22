/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package com.example.acctu.utils;

import com.huawei.hms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class MapUtils {



    public static final LatLng krct = new LatLng(78, 10);

    public static final LatLng source = new LatLng(72, 15);

    public static List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }

    public static final int MAP_TYPE_NONE = 0;

    public static final int MAP_TYPE_NORMAL = 1;

    public static final float MAX_ZOOM_LEVEL = 20;

    public static final float MIN_ZOOM_LEVEL = 3;

    public static final String API_KEY = "CgB6e3x9HT8wHBZoFsLF9L5r68jkBWYEZag7AQKBLbT8VObWOtmXjBNdKqdWQbiWNonajAxvNPWh6WoTdnNHmbxv";
}
