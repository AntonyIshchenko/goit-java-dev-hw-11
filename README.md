# Tomcat Servlet Project with Template Engine

Use the code from the previous homework as a foundation.

## Task #1 - Connect Thymeleaf
Connect the Thymeleaf template engine to the project. Move the time rendering to HTML templates. The servlet should:

- Handle the logic for calculating the current time in the appropriate time zone
- Pass the calculated parameters and corresponding HTML template to Thymeleaf
- Return the results of the Thymeleaf template processing from the servlet

## Task #2 - Save the Last Time Zone in a Cookie
Store the last valid time zone passed in the query parameter "timezone" in a Cookie named "lastTimezone" for the user.

If the user later tries to get data without passing the "timezone" parameter, you should try to retrieve this time zone from the Cookie. If there is no time zone in the Cookie either, then use the UTC time zone.

For example:
1. The user calls the URL `http://localhost:8080/time` for the first time. Display a result like `2022-01-05 10:05:01 UTC`.

2. Then the user calls the URL `http://localhost:8080/time?timezone=UTC+2`. Display a result like `2022-01-05 12:05:01 UTC+2`. Save `UTC+2` in the Cookie "lastTimezone".

3. Then the user calls the URL `http://localhost:8080/time`. Display a result like `2022-01-05 12:05:01 UTC+2`. The time zone `UTC+2` is taken from the Cookie "lastTimezone".