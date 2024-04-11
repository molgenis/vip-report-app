import { createSignal, ParentComponent, Show } from "solid-js";
import { Navbar } from "./components/Navbar.tsx";
import Notification from "./components/bulma/notification.tsx";

export type Error = {
  message: string;
};

export const App: ParentComponent = (props) => {
  const [error, setError] = createSignal<Error>();

  window.addEventListener("unhandledrejection", function () {
    setError({ message: "An unexpected error occurred" });
  });

  window.addEventListener("error", () => {
    setError({ message: "An unexpected error occurred" });
  });

  window.addEventListener("app_error", (event) => {
    setError({ message: (event as CustomEvent).detail as string });
  });

  return (
    <>
      <Navbar />
      <Show when={error()} keyed>
        {(error) => (
          <Notification type="danger" onClose={() => setError()}>
            {error.message}
          </Notification>
        )}
      </Show>
      <div class="container is-fluid">{props.children}</div>
    </>
  );
};
