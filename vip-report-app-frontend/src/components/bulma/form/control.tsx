import { ParentComponent } from "solid-js";

export const Control: ParentComponent<{}> = (props) => {
  return <div class="control">{props.children}</div>;
};
