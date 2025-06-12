import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            if appDelegate.root != nil {
                RootView(root: appDelegate.root!).ignoresSafeArea(.all)
            } else {
                Text("Launching... (placeholder???)")
            }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    var root: RootComponent? = nil

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {

        KoinInitializerKt.doInitKoin(driverFactory: DriverFactory())

        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle()),
            eventRepository: EventRepositoryProvider().provide(),
            venueRepository: VenueRepositoryProvider().provide(),
            expenseRepository: ExpenseRepositoryProvider().provide(),
            dispatchers: CoroutineDispatchersProvider().provide()
        )
        return true
    }
}
