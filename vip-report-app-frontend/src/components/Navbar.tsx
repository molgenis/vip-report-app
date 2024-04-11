import { Component } from "solid-js";
import logoImgUrl from "../assets/img/logo.png";

export const Navbar: Component = () => {
  return (
    <nav class="navbar is-fixed-top is-info" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <a class="navbar-item" href="/">
          <img src={logoImgUrl} alt="Logo" />
        </a>
      </div>
      <div class="navbar-menu">
        <div class="navbar-start">
          <a class="navbar-item" href="/">
            Variant Interpretation Pipeline
          </a>
        </div>
      </div>
    </nav>
  );
};
