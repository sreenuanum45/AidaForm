# OptiumWorks / Aidaform

This repository contains automated GUI tests using Selenium + TestNG (and some Playwright references). I've added reporting integration for both ExtentReports and Allure to make test results and screenshots easy to view.

This README explains how to build, run tests, and view reports locally and in CI.

---

## What I added

- ExtentReports (HTML) output: `target/extent-report/extent.html` (screenshots in `target/extent-report/screenshots/`).
- Allure results: `target/allure-results/` (JSON + attachments), configured via `src/test/resources/allure.properties`.
- TestNG `ITestListener` implementation: `src/test/java/listeners/TestListener.java` — attaches screenshots to both Extent and Allure on failure.
- Reporting helpers:
  - `src/test/java/reporting/ExtentManager.java`
  - `src/test/java/reporting/ExtentTestManager.java`
  - `src/test/java/reporting/AllureHelper.java`
- Screenshot helpers and driver getter in `src/test/java/Base/BaseClass.java` so the listener can capture screenshots.
- `pom.xml` updated with `io.qameta.allure:allure-testng` and the `allure-maven` plugin (optional).

---

## Prerequisites

- Java 8+ (project configured for Java 8)
- Maven 3.6+
- A browser driver available on PATH (e.g., chromedriver) or configured in your runtime environment.
- (Optional) Allure CLI installed if you prefer `allure serve` instead of `mvn allure:serve`.
  - On Windows you can install via Chocolatey or Scoop, or download the binary from Allure releases.

---

## Build and run tests (PowerShell)

1. Compile test sources only (fast check):

```powershell
cd D:\Myprojects\optiumworks
mvn -DskipTests=true test-compile
```

2. Run the full test suite (this will generate Extent and Allure artifacts):

```powershell
cd D:\Myprojects\optiumworks
mvn clean test
```

Notes:
- `mvn clean test` runs the TestNG suite defined in `src/test/resources/testng.xml` (the surefire plugin in `pom.xml` is configured to use that suite file).
- Tests run in parallel per the `testng.xml` settings (parallel="classes" thread-count="2").

---

## View Allure report

Option A — using the Maven plugin (no local Allure CLI required):

```powershell
cd D:\Myprojects\optiumworks
mvn allure:serve
```

Option B — using Allure CLI (if installed):

```powershell
cd D:\Myprojects\optiumworks
allure serve target/allure-results
```

If you prefer to generate a static Allure report folder for CI artifacts, run:

```powershell
cd D:\Myprojects\optiumworks
mvn allure:report
# will generate target/site/allure-maven-plugin or target/allure-report (depending on plugin and version)
```

---

## View ExtentReport (HTML)

After tests finish, open the Extent HTML:

```powershell
ii D:\Myprojects\optiumworks\target\extent-report\extent.html
```

Screenshots for failed tests are saved under:

```
D:\Myprojects\optiumworks\target\extent-report\screenshots\
```

The Extent report references those images using relative paths so they display in the HTML.

---

## Files of interest

- `pom.xml` — dependencies and plugins (Allure TestNG adapter + optional plugin included).
- `src/test/resources/testng.xml` — TestNG suite and the global listener registration (`listeners.TestListener`).
- `src/test/java/Base/BaseClass.java` — driver initialization and screenshot helper methods (`takeScreenshotAsBytes`, `takeScreenshotToFile`, `getDriver`).
- `src/test/java/listeners/TestListener.java` — listener that creates Extent tests and attaches Allure + Extent screenshots on failure.
- `src/test/java/reporting/*` — helper classes for Extent and Allure attachments.
- `src/test/resources/allure.properties` — Allure results directory configuration.

---

## Troubleshooting

- No screenshots saved / NullPointerException in listener:
  - Ensure the test instance extends or uses `BaseClass` so the listener can cast the test instance and access the `driver`.
  - Confirm the browser driver (chromedriver/geckodriver) is available and the `driver` was initialized before the failure occurs.

- Allure CLI `allure` command not found:
  - Use `mvn allure:serve` (Maven plugin) or install Allure CLI on Windows via Chocolatey / Scoop. Example Chocolatey:

```powershell
choco install allure
```

- Extent HTML shows broken image links:
  - Ensure the `target/extent-report/screenshots/` directory exists and contains the PNG files. The listener writes files there. If running on CI, ensure artifacts are preserved and paths are not altered.

- Running tests in CI:
  - Make sure the CI runner has a browser or uses headless mode. Capture screenshots in headless by configuring your browser options (e.g., ChromeOptions.addArguments("--headless=new")).
  - Install or provide the Allure binary if you need `allure serve` or generate Allure artifacts for pipeline publishing.

---

## Next steps and extras you may want

- Add `extent-config.xml` and load it in `ExtentManager` to customize theme/metadata.
- Add more descriptive test metadata (authors, categories) when creating Extent tests.
- Add CI steps to upload `target/allure-results` and `target/extent-report` as pipeline artifacts.
- Add optional integration to publish Allure report to a web server or convert to static HTML for QA review.

---

If you want, I can also:
- Add an example failing test that demonstrates screenshot attachments.
- Create an `extent-config.xml` and wire it into `ExtentManager`.
- Add a README section with CI YAML snippets (GitHub Actions / Azure Pipelines / Jenkins).

Tell me which extra you'd like and I'll add it next.

