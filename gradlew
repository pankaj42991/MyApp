#!/usr/bin/env sh

##############################################################################
## Gradle wrapper script for UNIX
##############################################################################

DIR="$(cd "$(dirname "$0")" && pwd)"
APP_BASE_NAME=$(basename "$0")

DEFAULT_JVM_OPTS=""

CLASSPATH="$DIR/gradle/wrapper/gradle-wrapper.jar"

exec java $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"