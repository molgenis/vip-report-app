import { Component, createSignal, Show } from "solid-js";
import { Login, User } from "../api/Api.ts";
import api from "../api/ApiClient.ts";
import { createStore } from "solid-js/store";
import Modal from "./bulma/modal.tsx";
import { Field, FieldLabel } from "./bulma/form/field.tsx";
import { Control } from "./bulma/form/control.tsx";

const LoginModal: Component<{
  onClose: () => void;
  onLogIn: (user: User) => void;
}> = (props) => {
  const [fields, setFields] = createStore<Login>({ username: "", password: "" });
  const [help, setHelp] = createSignal<string>();
  const handleLogin = async () => {
    try {
      const user = await api.login({ username: fields.username, password: fields.password });
      setFields("username", "");
      setFields("password", "");
      setHelp();
      props.onLogIn(user);
    } catch {
      setHelp("Invalid username and/or password");
    }
  };

  const handleCancel = () => {
    props.onClose();
    setFields("username", "");
    setFields("password", "");
    setHelp();
  };

  return (
    <Modal onClose={() => handleCancel()}>
      <div class="box">
        {/* username */}
        <Field horizontal>
          <FieldLabel size="normal">
            <label class="label">Username</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <Control>
                <input
                  classList={{ input: true }}
                  type="text"
                  value={fields.username}
                  onInput={(e) => {
                    setFields("username", e.target.value);
                  }}
                />
              </Control>
            </Field>
          </div>
        </Field>
        {/* password */}
        <Field horizontal>
          <FieldLabel size="normal">
            <label class="label">Password</label>
          </FieldLabel>
          <div class="field-body">
            <Field>
              <Control>
                <input
                  classList={{ input: true }}
                  type="password"
                  value={fields.password}
                  onInput={(e) => {
                    setFields("password", e.target.value);
                  }}
                />
              </Control>
              <Show when={help()} keyed>
                {(help) => <p class="help is-danger">{help}</p>}
              </Show>
            </Field>
          </div>
        </Field>
        {/* buttons */}
        <Field horizontal>
          <FieldLabel />
          <div class="field-body">
            <Field grouped="right">
              <Control>
                <button class="button is-link is-light" onClick={handleCancel}>
                  Cancel
                </button>
              </Control>
              <Control>
                <button class="button is-link" onClick={() => void handleLogin()}>
                  Log in
                </button>
              </Control>
            </Field>
          </div>
        </Field>
      </div>
    </Modal>
  );
};

export default LoginModal;
