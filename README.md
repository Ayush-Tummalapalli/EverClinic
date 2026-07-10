# EverClinic 🏥

EverClinic is a modern clinic management Android application designed to streamline daily healthcare operations. Built entirely in Kotlin using **Jetpack Compose** and following modern Android development best practices, it provides healthcare professionals with an intuitive interface to manage patients, doctors, appointments, and treatment records.

---

## 🚀 Features

*   **📊 Interactive Dashboard**: Quick access to clinic statistics, shortcuts for common actions, and summaries of upcoming appointments.
*   **👥 Patient Management**: Create, edit, and view comprehensive profiles for patients, including contact details and their historical treatments.
*   **🩺 Doctor Directory**: Maintain records of doctors, their specialties, contact info, and associated appointments.
*   **📅 Appointment Scheduler**: Schedule and reschedule appointments with validation logic to ensure smooth bookings.
*   **💊 Treatment Tracking**: Log medical treatments, link them with patients/doctors, and view treatment histories.

---

## 🛠️ Tech Stack & Libraries

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative UI design.
*   **Architecture**: MVVM (Model-View-ViewModel) pattern, enforcing separation of concerns and testability.
*   **Local Database**: [Room Database](https://developer.android.com/training/data-storage/room) (SQLite abstraction) for secure and efficient offline storage.
*   **Navigation**: Jetpack Compose Navigation for fluid transition between screens.
*   **Asynchronous Processing**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [StateFlow](https://kotlinlang.org/docs/flow.html) for reactive, thread-safe UI updates.

---

## 📁 Project Structure

```text
app/src/main/java/com/example/everclinic/
│
├── data/
│   ├── local/          # Room DB entities, DAOs, and Type Converters
│   ├── model/          # Clean domain/data representations (Patient, Doctor, etc.)
│   └── repository/     # Repository implementation coordinating local data operations
│
├── navigation/         # Navigation graph definition, routes, and screen paths
│
├── ui/                 # Composable components and screen layouts
│   ├── appointments/   # Appointment screen UIs and ViewModels
│   ├── components/     # Reusable UI elements (SearchBar, AvatarBadge, Cards)
│   ├── doctors/        # Doctor list, details, and form screens
│   ├── home/           # Dashboard / Home Screen layout and statistics logic
│   ├── patients/       # Patient directory, profile view, and edit screens
│   ├── theme/          # Custom Material3 styling, colors, and typography tokens
│   └── treatments/     # Log records and treatment details
│
├── uiState/            # State holders for the respective screens
└── util/               # Extension functions, date utilities, and input validation
```

---

## ⚙️ Getting Started

### Prerequisites

*   **Android Studio** (Ladybug or newer recommended)
*   **JDK 17** or higher
*   Android SDK (API level 24 minimum, targeting API level 34)

### Setup & Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Ayush-Tummalapalli/EverClinic.git
    cd EverClinic
    ```
2.  **Open in Android Studio:**
    *   Open Android Studio, select **File > Open**, and select the cloned directory.
    *   Allow Gradle build to complete synchronization.
3.  **Run on Device/Emulator:**
    *   Connect a physical device or start an Android Virtual Device (AVD).
    *   Click the **Run** button (green play icon) in Android Studio.
