import { useElementSize, debouncedWatch } from "@vueuse/core";
const resize = (el, binding) => {
    const { width } = useElementSize(el);
    if (width.value === 0) return;
    const { value } = binding;
    debouncedWatch(
        width,
        () => {
            value && value.resize();
        },
        { debounce: 500 },
    );
};
export default resize;
