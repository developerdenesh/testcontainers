# Use an official Gradle base image
FROM gradle:jdk17

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . /app/

# Build the project
RUN gradle build --no-daemon

# Run the tests
RUN gradle test --no-daemon