# Reverse Cursor

Cursor, but it adds a catch to every prompt.

Written in kotlin, it uses the gemini api to generate websites.

# Tech stack
- Kotlin
- Gemini API
- Gradle
- Ktor
- Jetpack Compose
- WebGL
- Material3

# What happens when you ask for something
- Your request goes to the Ktor server
- The ktor server finds or generates your prompt
- The prompt goes to gemini 2.5 flash thru one of the api keys
- The answer is concatinated onto your prompt and sent back to you
- Your client shows it alongside the link (via an iframe)
