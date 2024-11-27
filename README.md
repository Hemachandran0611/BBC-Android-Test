# Test Technical Assessments

This repository contains Android test automation scripts covering all the specified scenarios.

---

## Requirements 

### Android
- **Android Studio**: Version Ladybug or later  
- **Android SDK**: API Level 26 or higher  
 
---

## Overview

### Application Structure
1. **Home Page**: 
   - Central hub featuring links and actions like:
     - Topic Picker
     - Refresh Button
     - Breaking News Button
   - Includes navigation links to explore content-specific pages.

2. **Content Page**:
   - Displays detailed content for selected topics.
   - Includes scrolling functionality and a back button for navigation.

---

## Test Automation Implementation

### Tools and Frameworks
- **Jetpack Compose Testing** (Android): For UI interaction and validation.
- **ComposeTestRule**: Used for simulating and verifying UI behaviors.
- **JUnit**: Framework for organizing and running test scenarios.

### Test Structure
1. **Helpers**: Encapsulate reusable logic for interacting with specific parts of the UI:
   - `HomepageHelper`: Verifies and interacts with home page components.
   - `ContentPageHelper`: Manages interactions and verifications on content pages.

2. **Tests**: Organized by scenarios, each test validates specific UI flows and functionalities:
   - Scenario 1: Verify the home page loads successfully.
   - Scenario 2: Verify the refresh button updates the last updated time.
   - Scenario 3: Validate topic picker updates the "Go To" link.
   - Scenario 4: Navigate to a content page and back.
   - Scenario 5: Select "TV Guide" and cancel navigation.
   - Scenario 6: Select "TV Guide" and confirm navigation.
   - Scenario 7: Handle errors triggered by the "Breaking News" button.

---

## Getting Started

### Setting Up the Environment
1. **Fork the Repository**:  
   Fork the repository to your local machine.

2. **Open the Project in the Appropriate IDE**:  
   - For Android: Open the Android project in **Android Studio**.   
   Sync the project with Gradle files and rebuild it to ensure everything is configured properly.

3. **Install Dependencies**:  
   - For Android: Install required Android SDKs via **SDK Manager** in Android Studio.

---

### Running the Tests
 **Android**:
   - Navigate to the `test` directory in **Android Studio**.
   - Run the test file `HomePageTest.kt` to execute all scenarios.
   - Ensure an emulator or physical device is connected and configured.

---

### Folder Structure

- **`helpers/`**:
  - Contains reusable helper classes for managing interactions with UI components.
  - Examples: `HomepageHelper`, `ContentPageHelper`.

- **`tests/`**:
  - Contains scenario-specific test files like `HomePageTest.kt`.

- **`resources/`**:
  - Includes assets like constants and test tags for UI elements.


