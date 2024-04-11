import { ParentComponent } from "solid-js";

export type FieldGrouped = true | "centered" | "right";

export const Field: ParentComponent<{
  grouped?: FieldGrouped;
  horizontal?: boolean;
}> = (props) => {
  return (
    <div
      class="field"
      classList={{
        "is-grouped": props.grouped !== undefined,
        "is-grouped-centered": props.grouped === "centered",
        "is-grouped-right": props.grouped === "right",
        "is-horizontal": props.horizontal,
      }}
    >
      {props.children}
    </div>
  );
};

export type FieldSize = "small" | "normal" | "medium" | "large";

export const FieldLabel: ParentComponent<{
  size?: FieldSize;
}> = (props) => {
  return (
    <div class="field-label" classList={props.size ? { [`is-${props.size}`]: true } : undefined}>
      {props.children}
    </div>
  );
};

export const FieldBody: ParentComponent = (props) => {
  return <div class="field-body">{props.children}</div>;
};
