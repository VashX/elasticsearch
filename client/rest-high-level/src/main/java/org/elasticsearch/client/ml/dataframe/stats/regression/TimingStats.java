/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.client.ml.dataframe.stats.regression;

import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Objects;

public class TimingStats implements ToXContentObject {

    public static final ParseField ELAPSED_TIME = new ParseField("elapsed_time");
    public static final ParseField ITERATION_TIME = new ParseField("iteration_time");

    public static ConstructingObjectParser<TimingStats, Void> PARSER = new ConstructingObjectParser<>("regression_timing_stats", true,
        a -> new TimingStats(
            a[0] == null ? null : TimeValue.timeValueMillis((long) a[0]),
            a[1] == null ? null : TimeValue.timeValueMillis((long) a[1])
        ));

    static {
        PARSER.declareLong(ConstructingObjectParser.optionalConstructorArg(), ELAPSED_TIME);
        PARSER.declareLong(ConstructingObjectParser.optionalConstructorArg(), ITERATION_TIME);
    }

    private final TimeValue elapsedTime;
    private final TimeValue iterationTime;

    public TimingStats(TimeValue elapsedTime, TimeValue iterationTime) {
        this.elapsedTime = elapsedTime;
        this.iterationTime = iterationTime;
    }

    public TimeValue getElapsedTime() {
        return elapsedTime;
    }

    public TimeValue getIterationTime() {
        return iterationTime;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        if (elapsedTime != null) {
            builder.humanReadableField(ELAPSED_TIME.getPreferredName(), ELAPSED_TIME.getPreferredName() + "_string", elapsedTime);
        }
        if (iterationTime != null) {
            builder.humanReadableField(ITERATION_TIME.getPreferredName(), ITERATION_TIME.getPreferredName() + "_string", iterationTime);
        }
        builder.endObject();
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimingStats that = (TimingStats) o;
        return Objects.equals(elapsedTime, that.elapsedTime) && Objects.equals(iterationTime, that.iterationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elapsedTime, iterationTime);
    }
}
