import { Component, For } from "solid-js";
import { FilterTree, FilterTreeClass, FilterTreeClassId, FilterTreeType } from "../api/Api.ts";
import { FilterTreeEvent, FilterTreeInput } from "./FilterTreeInput.tsx";
import { Field, FieldLabel } from "./bulma/form/field.tsx";
import { Control } from "./bulma/form/control.tsx";

export type FilterTreeClassEvent = {
  treeClass: FilterTreeClass;
};

function findTreeClassById(tree: FilterTree, treeClassId: number) {
  const treeClass = tree.classes.find((treeClass) => treeClass.id === treeClassId);
  if (treeClass === undefined) throw new Error();
  return treeClass;
}

export const FilterTreeFields: Component<{
  type: keyof typeof FilterTreeType;
  filterTree: FilterTree;
  filterClassIds: FilterTreeClassId[];
  onSelectTree: (event: FilterTreeEvent) => void;
  onAddTreeClass: (event: FilterTreeClassEvent) => void;
  onRemoveTreeClass: (event: FilterTreeClassEvent) => void;
}> = (props) => {
  const onClassChange = (checked: boolean, filterClassId: number) => {
    const tree = props.filterTree;
    if (tree !== undefined) {
      const treeClass = findTreeClassById(tree, filterClassId);
      if (checked) props.onAddTreeClass({ treeClass });
      else props.onRemoveTreeClass({ treeClass });
    }
  };

  const label = () => {
    switch (props.type) {
      case "VARIANT":
        return "Variant filter";
      case "SAMPLE":
        return "Sample filter";
      default:
        throw `unexpected enum value`;
    }
  };
  return (
    <>
      <Field horizontal>
        <FieldLabel size="normal">
          <label class="label">{label()}</label>
        </FieldLabel>
        <div class="field-body">
          <div class="field is-narrow">
            <FilterTreeInput type={props.type} tree={props.filterTree} onSelectTree={props.onSelectTree} />
          </div>
        </div>
      </Field>
      <Field horizontal>
        <FieldLabel />
        <div class="field-body">
          <Field>
            <Control>
              <Field horizontal>
                <For each={props.filterTree.classes}>
                  {(filterTreeClass) => (
                    <div class="control mr-3">
                      <label class="checkbox">
                        <input
                          type="checkbox"
                          class="mr-1"
                          value={filterTreeClass.id}
                          checked={props.filterClassIds.includes(filterTreeClass.id)}
                          onInput={(e) => onClassChange(e.target.checked, Number(e.target.value))}
                        />
                        <abbr title={filterTreeClass.description}>{filterTreeClass.name}</abbr>
                      </label>
                    </div>
                  )}
                </For>
              </Field>
              <p class="help">
                For details, see the{" "}
                <a
                  href="https://molgenis.github.io/vip/advanced/classification_trees"
                  target="_blank"
                  rel="noopener noreferrer nofollow"
                >
                  documentation
                </a>
              </p>
            </Control>
          </Field>
        </div>
      </Field>
    </>
  );
};
