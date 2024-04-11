/* @refresh reload */
import "./assets/sass/main.scss";
import { render } from "solid-js/web";
import { HashRouter, Route } from "@solidjs/router";
import { App } from "./App";
import { dom, library } from "@fortawesome/fontawesome-svg-core";
import { Provider } from "./store/store.tsx";
import Home from "./views/Home.tsx";

library.add();

function processIcons() {
  void dom.i2svg();
  dom.watch();
}

if (document.readyState === "complete") {
  processIcons();
} else {
  window.addEventListener("DOMContentLoaded", processIcons);
}

render(
  () => (
    <Provider>
      <HashRouter root={App}>
        <Route path="/" component={Home} />
      </HashRouter>
    </Provider>
  ),
  document.body,
);
